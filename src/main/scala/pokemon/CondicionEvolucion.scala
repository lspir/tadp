package pokemon

abstract class CondicionEvolucion {
  def teCumple(pokemon:Pokemon):Boolean
}

case class SubirDeNivel(nivel:Int) extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    pokemon.nivel>=nivel
  }
}

case class Intercambiar() extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    true //FIXME
  }
}

case object CondicionUsarPiedraLunar extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    true
  }
}

case class CondicionUsarPiedra() extends CondicionEvolucion{
  def teCumple(pokemon:Pokemon):Boolean={
    true//pokemon.especie.tipoPrincipal.eq
  }
}

case class Piedra(tipo:Tipo)