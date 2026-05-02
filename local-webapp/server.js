const fs = require('fs');
const fsp = require('fs/promises');
const path = require('path');
const express = require('express');
const cors = require('cors');
const multer = require('multer');
const open = require('open');
const { chromium } = require('playwright');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;
const N8N_WEBHOOK_URL = process.env.N8N_WEBHOOK_URL;
const LOVABLE_BASE_URL = process.env.LOVABLE_BASE_URL || 'https://lovable.dev/chat?prompt=';

app.use(cors());
app.use(express.json({ limit: '10mb' }));
app.use(express.static(path.join(__dirname, 'public')));

const upload = multer({ storage: multer.memoryStorage() });

function ensureDir(dirPath) {
  if (!fs.existsSync(dirPath)) {
    fs.mkdirSync(dirPath, { recursive: true });
  }
}

async function generatePdfFromHtml(htmlContent, outputPath) {
  const browser = await chromium.launch({ headless: true });
  try {
    const page = await browser.newPage();
    await page.setContent(htmlContent, { waitUntil: 'networkidle' });
    await page.pdf({ path: outputPath, format: 'A4', printBackground: true });
  } finally {
    await browser.close();
  }
}

app.post('/api/process-transcript', upload.single('transcript'), async (req, res) => {
  try {
    if (!N8N_WEBHOOK_URL) {
      return res.status(500).json({ error: 'Falta configurar N8N_WEBHOOK_URL en el archivo .env' });
    }

    if (!req.file) {
      return res.status(400).json({ error: 'Debes subir un archivo .txt' });
    }

    const transcriptText = req.file.buffer.toString('utf-8');
    const fileName = req.file.originalname;

    const n8nResponse = await fetch(N8N_WEBHOOK_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        fileName,
        transcriptText,
      }),
    });

    if (!n8nResponse.ok) {
      const errorText = await n8nResponse.text();
      return res.status(502).json({ error: `Error desde n8n: ${errorText}` });
    }

    const n8nData = await n8nResponse.json();
    const requirementsHtml = n8nData.requirementsHtml || n8nData.html || '';
    const summaryTxt = n8nData.summaryTxt || n8nData.summary || '';

    if (!requirementsHtml || !summaryTxt) {
      return res.status(500).json({ error: 'n8n debe responder con requirementsHtml y summaryTxt' });
    }

    const stamp = new Date().toISOString().replace(/[:.]/g, '-');
    const outputDir = path.join(__dirname, 'outputs', stamp);
    ensureDir(outputDir);

    const htmlPath = path.join(outputDir, 'documento-requerimientos.html');
    const summaryPath = path.join(outputDir, 'resumen-requerimientos.txt');
    const pdfPath = path.join(outputDir, 'documento-requerimientos.pdf');

    await fsp.writeFile(htmlPath, requirementsHtml, 'utf-8');
    await fsp.writeFile(summaryPath, summaryTxt, 'utf-8');
    await generatePdfFromHtml(requirementsHtml, pdfPath);

    const lovableUrl = `${LOVABLE_BASE_URL}${encodeURIComponent(summaryTxt)}`;
    await open(lovableUrl);

    res.json({
      message: 'Flujo completado',
      files: {
        htmlPath,
        summaryPath,
        pdfPath,
      },
      lovableUrl,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error procesando el flujo', details: error.message });
  }
});

app.listen(PORT, () => {
  console.log(`Web app disponible en http://localhost:${PORT}`);
});
