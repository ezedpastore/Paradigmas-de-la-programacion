module Pred where

import Dibujo

type Pred a = a -> Bool

--Para la definiciones de la funciones de este modulo, no pueden utilizar
--pattern-matching, sino alto orden a traves de la función foldDib, mapDib

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por el resultado de llamar a la función indicada por el
-- segundo argumento con dicha figura.
-- Por ejemplo, `cambiar (== Triangulo) (\x -> Rotar (Basica x))` rota
-- todos los triángulos.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar pred fun d = 
                foldDib 
                    (\x -> if pred x then fun x else pureDib x)
                    rotar
                    rotar45
                    espejar
                    apilar
                    juntar
                    encimar
                    d

-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib pred d =
            foldDib
                pred
                id
                id
                id
                (\_ _ x y -> x || y)
                (\_ _ x y -> x || y)
                (\x y -> x || y)
                d

-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib pred d =
            foldDib
                pred
                id
                id
                id
                (\_ _ x y -> x && y)
                (\_ _ x y -> x && y)
                (\x y -> x && y)
                d

-- Hay 4 rotaciones seguidas.
esRot360 :: Pred (Dibujo a)
esRot360 d =
        (foldDib
            (\x -> 0)
            (\x -> if x == 0 then 1 else x + 1)
            (\x -> if x >= 4 then x else 0)
            (\x -> if x >= 4 then x else 0)
            (\_ _ x y -> if x >= 4 || y >= 4 then max x y else 0)
            (\_ _ x y -> if x >= 4 || y >= 4 then max x y else 0)
            (\x y -> if x >= 4 || y >= 4 then max x y else 0)
            d) >= 4

-- Hay 2 espejados seguidos.
esFlip2 :: Pred (Dibujo a)
esFlip2 d =
        (foldDib
            (\x -> 0)
            (\x -> if x >= 2 then x else 0)
            (\x -> if x >= 2 then x else 0)
            (\x -> if x == 0 then 1 else x + 1)
            (\_ _ x y -> if x >= 2 || y >= 2 then max x y else 0)
            (\_ _ x y -> if x >= 2 || y >= 2 then max x y else 0)
            (\x y -> if x >= 2 || y >= 2 then max x y else 0)
            d) >= 2

data Superfluo = RotacionSuperflua | FlipSuperfluo

---- Chequea si el dibujo tiene una rotación superflua
errorRotacion :: Dibujo a -> [Superfluo]
errorRotacion d = if esRot360 d then [RotacionSuperflua] else []

-- Chequea si el dibujo tiene un flip superfluo
errorFlip :: Dibujo a -> [Superfluo]
errorFlip d = if esFlip2 d then [FlipSuperfluo] else []

-- Aplica todos los chequeos y acumula todos los errores, y
-- sólo devuelve la figura si no hubo ningún error.
checkSuperfluo :: Dibujo a -> Either [Superfluo] (Dibujo a)
checkSuperfluo d =
        let err = (errorRotacion d) ++ (errorFlip d) in
            case err of
                [] -> Right d
                xs -> Left xs

