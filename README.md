# NDA

A simple plugin that allows you to prevent opening files like .env.

## Configure `.nda.json`

You use the `.nda.json` file to tell the plugin which files should be disallowed to open.

```json
{
  "enabled": true,
  "disallow": [
    "/.env"
  ]
}
```