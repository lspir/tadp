package pokemon

trait CondicionEvolucion {
  def teCumple(pokemon:Pokemon):Boolean={false}
  def teCumple(pokemon:Pokemon,piedra:Piedra):Boolean={false}
}

case class SubirDeNivel(nivel:Int) extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon):Boolean={
    pokemon.nivel>=nivel
  }
}

case class Intercambiar() extends CondicionEvolucion{
//  def teCumple(pokemon:Pokemon):Boolean={
//    true //FIXME
//  }
}

case object CondicionUsarPiedraLunar extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon,piedra:Piedra):Boolean={
    piedra.tipo.equals(Lunar)
  }
}

case class CondicionUsarPiedra() extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon,piedra:Piedra):Boolean={
    piedra.tipo.equals(pokemon.especie.tipoPrincipal)
  }
}

case class Piedra(tipo:Tipo)