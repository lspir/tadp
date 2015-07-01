abstract class Estado {
}

case class Dormido(turnosRestantes:Int=3) extends Estado{
  
  def dormir()={
    turnosRestantes match{
      case 1 => Normal
      case _ => copy(turnosRestantes = turnosRestantes - 1)
    }
    
  }
}
case object Normal extends Estado
case class Envenenado() extends Estado
case class Paralizado() extends Estado
case class KO() extends Estado
