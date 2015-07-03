package pokemon

trait Estado
case class Dormido(turnosRestantes:Int=3) extends Estado {
  val dormir:Estado = {
    turnosRestantes match {
      case 1 => EstadoNormal;
      case _ => copy(turnosRestantes = turnosRestantes - 1)
    }
  }
}
case object EstadoNormal extends Estado
case object Envenenado extends Estado
case object Paralizado extends Estado
case object KO extends Estado
