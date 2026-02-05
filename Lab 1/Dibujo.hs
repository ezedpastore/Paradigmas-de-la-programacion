module Dibujo where

-- Definir el lenguaje via constructores de tipo
data Dibujo a = Basica a
              | Rotar (Dibujo a)
              | Rotar45 (Dibujo a)
              | Espejar (Dibujo a)
              | Apilar Float Float (Dibujo a) (Dibujo a)
              | Juntar Float Float (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              deriving (Eq, Show)

basica :: a -> Dibujo a
basica b = Basica b

rotar :: Dibujo a -> Dibujo a
rotar d = Rotar d

rotar45 :: Dibujo a -> Dibujo a
rotar45 d = Rotar45 d

espejar :: Dibujo a -> Dibujo a
espejar d = Espejar d

apilar :: Float -> Float -> (Dibujo a) -> (Dibujo a) -> (Dibujo a)
apilar x y d1 d2 = Apilar x y d1 d2

juntar :: Float -> Float -> (Dibujo a) -> (Dibujo a) -> (Dibujo a)
juntar x y d1 d2 = Juntar x y d1 d2

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar d1 d2 = Encimar d1 d2

-- Composición n-veces de una función con sí misma.
comp :: (a -> a) -> Int -> a -> a
comp _ 0 b = b
comp f n b = f (comp f (n - 1) b)

-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 d = comp rotar 2 d

r270 :: Dibujo a -> Dibujo a
r270 d = comp rotar 3 d


-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) d1 d2 = apilar 1 1 d1 d2

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) d1 d2 = juntar 1 1 d1 d2

-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) d1 d2 = encimar d1 d2


-- Dadas cuatro dibujos las ubica en los cuatro cuadrantes.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto d1 d2 d3 d4 = (d1 /// d2) .-. (d3 /// d4)

-- Una dibujo repetido con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 d = d ^^^ (rotar d) ^^^ (r180 d) ^^^ (r270 d)

-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar d = cuarteto d (rotar d) (r180 d) (r270 d)


-- Transformar un valor de tipo a como una Basica.
pureDib :: a -> Dibujo a
pureDib b = basica b

-- map para nuestro lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b     
mapDib f (Basica b) =              Basica (f b)
mapDib f (Rotar d) =               Rotar (mapDib f d)
mapDib f (Rotar45 d) =             Rotar45 (mapDib f d)
mapDib f (Espejar d) =             Espejar (mapDib f d)
mapDib f (Apilar f1 f2 d1 d2) =    Apilar f1 f2 (mapDib f d1) (mapDib f d2)
mapDib f (Juntar f1 f2 d1 d2) =    Juntar f1 f2 (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) =         Encimar (mapDib f d1) (mapDib f d2)

-- Función de fold para Dibujos a
foldDib ::
       (a -> b) ->
       (b -> b) ->
       (b -> b) ->
       (b -> b) ->
       (Float -> Float -> b -> b -> b) ->
       (Float -> Float -> b -> b -> b) ->
       (b -> b -> b) ->
       Dibujo a ->
       b
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar dibujo =
       case dibujo of
              (Basica b)           ->   fBasica b
              (Rotar d)            ->   fRotar   (recursion d)
              (Rotar45 d)          ->   fRotar45 (recursion d)
              (Espejar d)          ->   fEspejar (recursion d)
              (Apilar f1 f2 d1 d2) ->   fApilar f1 f2 (recursion d1) (recursion d2)
              (Juntar f1 f2 d1 d2) ->   fJuntar f1 f2 (recursion d1) (recursion d2)
              (Encimar d1 d2)      ->   fEncimar (recursion d1) (recursion d2)
       where
              recursion = foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar
