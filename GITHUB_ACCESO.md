# Guía: Cómo ingresar y usar GitHub

Esta guía explica las formas más comunes de acceder a GitHub y cómo configurar acceso desde tu equipo.

## 1) Acceso por web
1. Abre https://github.com en tu navegador.
2. Haz clic en "Sign in" (o "Iniciar sesión").
3. Ingresa tu usuario/correo y contraseña. Si tienes 2FA habilitado, introduce el código del autenticador o la clave de seguridad.

## 2) Acceso con SSH (recomendado para desarrolladores)
SSH permite autenticarte sin ingresar tu usuario/contraseña cada vez.

1. Generar clave SSH (PowerShell):

```powershell
ssh-keygen -t ed25519 -C "tu.email@example.com"
```
- Acepta la ubicación por defecto (`%USERPROFILE%/.ssh/id_ed25519`).
- Opcional: añade una passphrase para mayor seguridad.

2. Iniciar el agente SSH y añadir la clave:

```powershell
Start-Service ssh-agent
ssh-add $env:USERPROFILE\.ssh\id_ed25519
```

3. Copiar la clave pública al portapapeles y añadir en GitHub:

```powershell
Get-Content $env:USERPROFILE\.ssh\id_ed25519.pub | Set-Clipboard
```
- Ve a GitHub → Settings → SSH and GPG keys → New SSH key → pega la clave y guarda.

4. Probar conexión SSH:

```powershell
ssh -T git@github.com
```

## 3) Acceso con HTTPS y Personal Access Token (PAT)
Si prefieres HTTPS o no puedes usar SSH, usa un token en lugar de la contraseña.

1. Generar un PAT en GitHub: GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic) → Generate new token. Elige scopes `repo` (y `workflow` si usarás Actions).
2. Al hacer `git push` la primera vez, usa tu usuario GitHub y pega el token como contraseña.
3. Para no introducirlo cada vez, configura el helper de credenciales en Windows:

```powershell
git config --global credential.helper manager-core
# o si no está instalado
git config --global credential.helper wincred
```

## 4) Acceso con GitHub Desktop (GUI)
1. Descarga GitHub Desktop: https://desktop.github.com/
2. Inicia sesión con GitHub (se abrirá el navegador para autorizar).
3. Clona repositorios, crea ramas y sincroniza cambios desde la interfaz.

## 5) Uso básico de Git por terminal
- Clonar:

```powershell
git clone git@github.com:Usuario/Repo.git
# o HTTPS
git clone https://github.com/Usuario/Repo.git
```

- Añadir, commitear y pushear:

```powershell
git add .
git commit -m "Mensaje"
git push
```

- Crear y cambiar a rama:

```powershell
git checkout -b nueva-rama
```

## 6) Solución de problemas
- `remote: Repository not found`: revisa la URL del remoto y permisos.
- `Permission denied (publickey)`: revisa que la clave SSH esté añadida en GitHub y cargada en el agente.
- Problemas con credenciales en HTTPS: configura `credential.helper` o usa PAT.

---
Si deseas, puedo:
- Generar la clave SSH aquí en tu equipo y ayudarte a pegarla en GitHub (necesitarás copiarla desde el portapapeles),
- Configurar `credential.helper manager-core` (requiere instalar Git Credential Manager), o
- Crear capturas de pantalla o un video corto con los pasos.

Dime qué prefieres y procedo.