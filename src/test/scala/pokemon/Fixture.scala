package pokemon

import scala.util.Try

case object Fixture {
  val zapdosEspecie=new Especie(10,100,500, Electrico,Some( Volador))
  val zapdos=new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,zapdosEspecie,Map())
  val zapdosHembra=new Pokemon(0,Hembra,1,100,10,1,1,EstadoNormal,zapdosEspecie,Map())
  
  val pikachuEspecie=new Especie(10,100,500, Electrico)
  
  val charizardEspecie= new Especie(20,1,350, Fuego)
  val charmeleonEspecie=new Especie(15,2,350, Fuego,evolucion=Some(new Evolucion(charizardEspecie,new SubirDeNivel(36))))
  val charmanderEspecie=new Especie(10,350,100, Fuego,evolucion=Some(new Evolucion(charmeleonEspecie,new SubirDeNivel(16))))
  val charmander= new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,charmanderEspecie,Map())

  val pikachu= new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,pikachuEspecie,Map())
  val machampEspecie= new Especie(15,100,150, Tierra)
  val machokeEspecie=new Especie(10,90,110, Tierra,evolucion=Some(new Evolucion(machampEspecie,Intercambiar)))
  val machopEspecie=new Especie(10,30,100, Tierra,evolucion=Some(new Evolucion(machokeEspecie,new SubirDeNivel(28))))
  val machoke=pokemonNormalConTipo(TipoNormal).copy(especie=machokeEspecie)
  
  val pokemonFuego= pokemonNormalConTipo(Fuego)
  val pokemonFantasma = pokemonNormalConTipo(Fantasma)
  val pokemonPelea = pokemonNormalConTipo(Pelea)
  val pokemonAgua = pokemonNormalConTipo(Agua)
  val pokemonEvolucionPiedraLunar= pokemonNormalConTipo(TipoNormal).copy(especie=new Especie(15,100,150, Tierra,evolucion=Some(new Evolucion(machampEspecie,CondicionUsarPiedraLunar))))
  val pokemonEvolucionPiedraTipoTierra= pokemonNormalConTipo(TipoNormal).copy(especie=new Especie(15,100,150, Tierra,evolucion=Some(new Evolucion(machampEspecie,new CondicionUsarPiedra))))
  
  
  val ataqueDragon=new Ataque(Dragon,30)
  val mordida=new Ataque( TipoNormal,30)
  val efectoReposar:Pokemon=>Pokemon={case p:Pokemon=>p.copy(energiaOriginal=p.energiaMaxima,estado=new Dormido())}
  val reposar=new Ataque( TipoNormal,30,Some(efectoReposar))
  val efectoEnforcarse:Pokemon=>Pokemon={case p:Pokemon=>p.copy(velocidadOriginal=p.velocidadOriginal+1)}
  val enfocarse=new Ataque( TipoNormal,30,Some(efectoEnforcarse))
  val efectoEndurecerse:Pokemon=>Pokemon={case p:Pokemon=>p.copy(energiaOriginal=p.energiaOriginal+5,estado=Paralizado)}
  val endurecerse= new Ataque( TipoNormal,30,Some(efectoEndurecerse))
  val ataqueElectrico= new Ataque( Electrico,35)
  val ataqueVolador= new Ataque( Volador,5)
  
  val criterioMayorExperiencia:(Pokemon=>Pokemon=>Boolean)={case p:Pokemon =>{case p2:Pokemon=> p.experiencia>p2.experiencia}}
  val rutinaRealizarAtaque:(String,List[Pokemon=>Try[Pokemon]])=("RutinaAtaque",List({_.realizarUnAtaque(ataqueDragon)}))
  val rutinaAtaquePesas10Kilos:(String,List[Pokemon=>Try[Pokemon]])=("RutinaAtaquePesas",List({_.realizarUnAtaque(ataqueDragon)},{_.levantarPesas(10)}))

  def pokemonNormalConTipo(tipo:Tipo):Pokemon={
    new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,new Especie(10,10,100, tipo),Map())
  }
}