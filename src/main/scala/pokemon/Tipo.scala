package pokemon

case class Tipo(leGanaA: Tipo*){
  val ganasContra:Tipo=>Boolean = {case tipo:Tipo=>leGanaA.map 
                            {t=>if(t==Self) tipo else t }.contains(tipo)}
  val perdesContra:Tipo=>Boolean = _.ganasContra(this)
}

class T{def gana: List[T]}

object Tipo {
  case object X extends T{def gana = List(X)}
  
  lazy val Dragon : Tipo = Tipo(Dragon)
}
case object TipoNormal extends Tipo
case object Fuego extends Tipo(List(Planta, Hielo, Bicho))
case object Agua extends Tipo(List(Fuego, Tierra, Roca))
case object Planta extends Tipo(List(Agua, Tierra, Roca))
case object Tierra extends Tipo(List(Fuego, Electrico, Volador, Roca))
case object Hielo extends Tipo(List(Planta, Tierra, Volador, Dragon))
case object Roca extends Tipo(List(Fuego, Hielo, Volador, Bicho))
case object Electrico extends Tipo(List(Agua, Volador))
case object Psiquico extends Tipo(List(Pelea, Veneno))
case object Pelea extends Tipo(List(TipoNormal, Hielo, Roca))
case object Fantasma extends Tipo(List(Self,Psiquico))
case object Volador extends Tipo(List(Planta, Hielo, Bicho))
case object Bicho extends Tipo(List(Planta, Psiquico))
case object Veneno extends Tipo(List(Planta))
case object Dragon extends Tipo(List(Dragon))
case object Lunar extends Tipo
case object Self extends Tipo