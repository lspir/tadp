package pokemon

abstract class CondicionEvolucion {
  def teCumple(pokemon:Pokemon):Boolean
}

case class subirDeNivel(nivel:Int) extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    pokemon.nivel>=nivel
  }
}

case class intercambiar() extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    true //FIXME
  }
}

case class usarPiedra() extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    true //FIXME
  }
}