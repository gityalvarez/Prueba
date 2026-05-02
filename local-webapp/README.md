# Web app local: transcripción -> n8n -> PDF -> Lovable

## Requisitos
- Node.js 18+
- Configurar variables de entorno

## Configuración
1. Copiar `.env.example` a `.env`
2. Completar:
   - `N8N_WEBHOOK_URL`: webhook de n8n que recibe `{ fileName, transcriptText }`
   - `LOVABLE_BASE_URL`: URL base para invocar lovable con el resumen (por defecto usa `?prompt=`)

## Instalación y ejecución
```bash
cd local-webapp
npm install
npx playwright install chromium
npm start
```

Abrir `http://localhost:3000`.

## Contrato esperado de n8n
El webhook debe responder JSON:
```json
{
  "requirementsHtml": "<html>...</html>",
  "summaryTxt": "Resumen de requerimientos..."
}
```

## Salidas
Cada ejecución guarda en `local-webapp/outputs/<timestamp>/`:
- `documento-requerimientos.html`
- `resumen-requerimientos.txt`
- `documento-requerimientos.pdf`
