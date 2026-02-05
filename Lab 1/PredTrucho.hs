module PredTrucho where

import Dibujo

-- `Pred a` define un predicado sobre figuras básicas. Por ejemplo,
-- `(== Triangulo)` es un `Pred TriOCuat` que devuelve `True` cuando la
-- figura es `Triangulo`.​
type Pred a = a -> Bool

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen​
-- el predicado por el resultado de llamar a la función indicada por el
-- segundo argumento con dicha figura.​
-- Por ejemplo, `cambiar (== Triangulo) (\x -> Rotar (Basica x))` rota​
-- todos los triángulos.​
cambiar2 :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar2 p f d = foldDib (\x -> if (p x) == True then f x else pureDib x) rotar rotar45 espejar apilar juntar encimar d

cambiar2m :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar2m pred f d = mapDib (\x -> if (pred x) == True then f x else pureDib x) d 

-- Alguna Basica satisface el predicado.​
anyDib2 :: Pred a -> Dibujo a -> Bool
anyDib2 pred d = foldDib (\x -> pred x) id id id (\f1 f2 d1 d2 -> d1 || d2) (\f1 f2 d1 d2 -> d1 || d2) (\d1 d2 -> d1 || d2) d

-- Todas las basica satisfacen el predicado.​
allDib2 :: Pred a -> Dibujo a -> Bool
allDib2 pred d = foldDib (\x -> pred x) id id id (\f1 f2 d1 d2 -> d1 && d2) (\f1 f2 d1 d2 -> d1 && d2) (\d1 d2 -> d1 && d2) d

-- Hay 4 rotaciones seguidas.
esRot3602 :: Pred (Dibujo a)
esRot3602 d = (foldDib (\x -> 0) (\d -> 1 + d) (\d -> if d < 4 then 0 else d) (\d -> if d < 4 then 0 else d) (\f1 f2 d1 d2 -> if (d1 < 4 && d2 < 4) then 0 else max d1 d2) (\f1 f2 d1 d2 -> if (d1 < 4 && d2 < 4) then 0 else max d1 d2) (\d1 d2 -> if (d1 < 4 && d2 < 4) then 0 else max d1 d2) d) >= 4 

-- Hay 2 espejados seguidos.
esFlip22 :: Pred (Dibujo a)
esFlip22 d = (foldDib (\x -> 0) (\d -> if d < 2 then 0 else d) (\d -> if d < 2 then 0 else d) (\d -> 1 + d) (\f1 f2 d1 d2 -> if (d1 < 2 && d2 < 2) then 0 else max d1 d2) (\f1 f2 d1 d2 -> if (d1 < 2 && d2 < 2) then 0 else max d1 d2) (\d1 d2 -> if (d1 < 2 && d2 < 2) then 0 else max d1 d2) d) >= 2