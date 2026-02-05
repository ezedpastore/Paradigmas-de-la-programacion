module Basica.BasicaDoble where
import Dibujo
import Interp
import Basica.Comun

data Basica = Triangulo | TrianguloVioleta | TrianguloRecto | TrianguloBorde | FormaF | Nada

ejemplo :: Dibujo Basica
ejemplo = Apilar 1 1 (Basica Triangulo) (Rotar (Basica TrianguloVioleta))

interpBas :: Basica -> ImagenFlotante
interpBas Triangulo = triangulo
interpBas TrianguloVioleta = trianguloVioleta
interpBas TrianguloRecto = trianguloRecto
interpBas TrianguloBorde = trianguloBorde
interpBas FormaF = formaF
interpBas Nada = nada
