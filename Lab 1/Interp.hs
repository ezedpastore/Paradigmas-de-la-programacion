-- Sacar del esqueleto final!
module Interp where
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo

-- Gloss provee el tipo Vector y Picture.
type ImagenFlotante = Vector -> Vector -> Vector -> Picture
type Interpretacion a = a -> ImagenFlotante

mitad :: Vector -> Vector
mitad = (0.5 V.*)

-- Interpretaciones de los constructores de Dibujo

--interpreta el operador de rotación
interp_rotar :: ImagenFlotante -> ImagenFlotante
interp_rotar f = (\d w h -> f (d V.+ w) h (V.negate w))

--interpreta el operador de espejar
interp_espejar :: ImagenFlotante -> ImagenFlotante
interp_espejar f = (\d w h -> f (d V.+ w) (V.negate w) h)

--interpreta el operador de rotación 45
-- TODO: Ver el tema de que al realizar la transformación la figura sale un poco de la pantalla
interp_rotar45 :: ImagenFlotante -> ImagenFlotante
interp_rotar45 f = (\d w h ->
    let
        d' = d V.+ mitad (w V.+ h)
        w' = mitad (w V.+ h)
        h' = mitad (h V.- w)
    in f d' w' h'
    )


--interpreta el operador de apilar
interp_apilar :: Int -> Int -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_apilar m n f g = (\d w h ->
        let
            r = fromIntegral m / fromIntegral (m + n)
            r' = fromIntegral n / fromIntegral (m + n)
            h' = r' V.* h
            in  pictures[f (d V.+ h') w (r V.* h), g d w h']
    )

--interpreta el operador de juntar
interp_juntar :: Int -> Int -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_juntar m n f g = \d w h ->
    let
        r  = fromIntegral m / fromIntegral (m+n)
        r' = fromIntegral n / fromIntegral (m+n)
        w' = r V.* w
        pic1' = f d w' h
        pic2' = g (d V.+ w') (r' V.* w) h
    in pictures [pic1', pic2']

--interpreta el operador de encimar
interp_encimar :: ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_encimar f g = (\d w h -> pictures [f d w h, g d w h])

--interpreta cualquier expresión del tipo Dibujo a
--utilizar foldDib
interp :: Interpretacion a -> Dibujo a -> ImagenFlotante
interp interp_basica d =
        foldDib
            interp_basica
            interp_rotar
            interp_rotar45
            interp_espejar
            (\f1 f2 d1 d2 -> interp_apilar (round f1) (round f2) d1 d2)
            (\f1 f2 d1 d2 -> interp_juntar (round f1) (round f2) d1 d2)
            interp_encimar
            d

