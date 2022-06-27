# NDA Plugin for IntelliJ-based IDEs

A simple plugin that allows you to prevent opening files like `.env`.

## Getting Started

1. Install a compatible JetBrains IDE, such as IntelliJ IDEA, CLion, PyCharm, or other IntelliJ-based IDEs
2. Launch the IDE and open plugin settings
3. Search for "NDA" and click install
4. Create a new `.nda.json` within the **base path** of your project
5. Add files like `/.env` into the `disallow` section within your `.nda.json` file

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
