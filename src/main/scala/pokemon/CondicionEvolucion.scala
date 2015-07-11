package pokemon

trait CondicionEvolucion {
  def teCumple(pokemon:Pokemon,piedra:Option[Piedra]=None):Boolean={false}
}

case class SubirDeNivel(nivel:Int) extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon,piedra:Option[Piedra]):Boolean={
    piedra.isEmpty && pokemon.nivel>=nivel
  }
}

case object Intercambiar extends CondicionEvolucion{
}

case object CondicionUsarPiedraLunar extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon,piedra:Option[Piedra]):Boolean={
    piedra.exists { p => p.tipo.equals(Lunar) }
  }
}

case class CondicionUsarPiedra() extends CondicionEvolucion{
  override def teCumple(pokemon:Pokemon,piedra:Option[Piedra]):Boolean={
    piedra.exists { p => p.tipo.equals(pokemon.especie.tipoPrincipal) }
  }
}

case class Piedra(tipo:Tipo)