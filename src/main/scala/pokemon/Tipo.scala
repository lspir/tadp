package pokemon

trait Tipo{
  val leGanaA:List[Tipo]
  val ganasContra:Tipo=>Boolean = leGanaA.contains(_)
  val perdesContra:Tipo=>Boolean = _.ganasContra(this)
}

case object Dragon extends Tipo{lazy val leGanaA=List(Dragon)}
case object TipoNormal extends Tipo{lazy val leGanaA=List()}
case object Fuego extends Tipo{lazy val leGanaA=List(Planta, Hielo, Bicho)}
case object Agua extends Tipo{lazy val leGanaA=List(Fuego, Tierra, Roca)}
case object Planta extends Tipo{lazy val leGanaA=List(Agua, Tierra, Roca)}
case object Tierra extends Tipo{lazy val leGanaA=List(Fuego, Electrico, Volador, Roca)}
case object Hielo extends Tipo{lazy val leGanaA=List(Planta, Tierra, Volador, Dragon)}
case object Electrico extends Tipo{lazy val leGanaA=List(Agua, Volador)}
case object Psiquico extends Tipo{lazy val leGanaA=List(Pelea, Veneno)}
case object Fantasma extends Tipo{lazy val leGanaA:List[Tipo]=List(Fantasma,Psiquico)}
case object Pelea extends Tipo{lazy val leGanaA=List(TipoNormal, Hielo, Roca)}
case object Volador extends Tipo{lazy val leGanaA:List[Tipo]=List(Planta, Hielo, Bicho)}
case object Bicho extends Tipo{lazy val leGanaA=List(Planta, Psiquico)}
case object Veneno extends Tipo{lazy val leGanaA=List(Planta)}
case object Lunar extends Tipo{lazy val leGanaA=List()}
case object Roca extends Tipo{lazy val leGanaA:List[Tipo]=List(Fuego, Hielo, Volador, Bicho)}

