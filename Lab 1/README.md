# 🖌️ MiniLenguaje - Intérprete Gráfico en Haskell

## 👥 Grupo 12 - NaN: Not a Name - UNC - Paradigmas - 2025

*Integrantes:*
- Canovas, José Lorenzo
- Garione, Facundo Gabriel
- Pastore, Ezequiel David

---

## 📌 Descripción

Este proyecto forma parte de un laboratorio de Paradigmas, donde hay que desarrollar un lenguaje descriptivo minimalista para la creación de dibujos utilizando la librería *Gloss* en *Haskell*.

El lab nos desafía a pensar en cómo construir un lenguaje descriptivo, enfrentarnos a la interpretación y renderización gráfica de comandos, todo esto bajo el *paradigma funcional*. Una experiencia interesante para el grupo, ya que fue la primera vez realizando una aplicación con un lenguaje puramente funcional.

---

## ⚙️ Instalación

1. Tener instalado *GHC* y *cabal*.
2. Instalá las dependencias necesarias (en particular gloss):
```bash
cabal update
cabal install --lib gloss
cabal install --lib GLUT
```

3. Cloná este repositorio:
```bash
git clone https://github.com/Paradigmas25-g12/paradigmas25-g12-lab1.git
cd paradigmas25-g12-lab1
```

---

## ▶️ Ejecución

1. Navegá al directorio donde se encuentra Main.hs.
2. Compilá el programa:
```bash
ghc Main.hs
```

3. Ejecutá el binario generado:
```bash
./Main
```

También podés correrlo directamente con runghc si no querés compilar (Re piola):
```bash
runghc Main.hs
```
---

## 🤔 ¿Cómo correr Escher?
📝 Para correr el dibujo Escher tuvimos que hacer lo siguiente:
1. Importar el archivo del Escher
```haskell
import qualified Basica.Escher as Es
```
2. En los ejemplos del main, cambiar el ejemplo que estaba por el de escher

Antes:
```haskell
ej ancho alto = Conf {
                basic = E.interpBas         -- Lugar a modificar
              , fig = E.ejemplo             -- Lugar a modificar
              , width = ancho
              , height = alto
              , r = id
              }

-- Code ...

ejCentro ancho alto = Conf {
                basic = E.interpBas         -- Lugar a modificar
              , fig = E.ejemplo             -- Lugar a modificar
              , width = ancho
              , height = alto
              , r = moverCentro ancho alto
              }
```
Después:
```haskell
ej ancho alto = Conf {
                basic = Es.interpBas        -- Lugar modificado
              , fig = Es.figura4            -- Lugar modificado
              , width = ancho
              , height = alto
              , r = id
              }

-- Code ...

ejCentro ancho alto = Conf {
                basic = Es.interpBas        -- Lugar modificado
              , fig = Es.figura4            -- Lugar modificado
              , width = ancho
              , height = alto
              , r = moverCentro ancho alto
              }
```
3. Cambiar el constructor usado en main

Antes:
```haskell
inicial :: IO (Conf E.Basica) -> IO ()
```
Después:
```haskell
inicial :: IO (Conf Es.Escher) -> IO ()
```

> Nota: Las figuras creadas con Escher son: figura1, ..., figura4 y escherTrianguloVioleta. ***Las que mas gustaron son las ultimas 2.***

## Agradecimientos

Queremos agradecer al equipo docente por la dedicación y la creatividad al plantear estos desafíos. *¡Muchas gracias! 👍🏻👍🏻*