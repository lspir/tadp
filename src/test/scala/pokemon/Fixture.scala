package pokemon

import scala.util.Try

case object Fixture {
  val zapdosEspecie=new Especie(10,100,500,Electrico,Some(Volador))
  val zapdos=new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,zapdosEspecie,Map())
  
  val pikachuEspecie=new Especie(10,100,500,Electrico)
  
  val charizardEspecie= new Especie(20,1,200,Fuego)
  val charmeleonEspecie=new Especie(15,2,150,Fuego,evolucion=Some(new Evolucion(charizardEspecie,new SubirDeNivel(36))))
  val charmanderEspecie=new Especie(10,350,100,Fuego,evolucion=Some(new Evolucion(charmeleonEspecie,new SubirDeNivel(16))))
  val charmander= new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,charmanderEspecie,Map())

  val pikachu= new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,pikachuEspecie,Map())
  val machampEspecie= new Especie(15,100,150,Tierra)
  val machokeEspecie=new Especie(10,90,110,Tierra,evolucion=Some(new Evolucion(machampEspecie,Intercambiar)))
  val machopEspecie=new Especie(10,30,100,Tierra,evolucion=Some(new Evolucion(machokeEspecie,new SubirDeNivel(28))))
  
  val pokemonFuego= new Pokemon(0,Macho,1,100,0,1,1,EstadoNormal,new Especie(10,10,100,Fuego),Map())
  
  
  val mordida=new Ataque(TipoNormal,30)
  val efectoReposar:Pokemon=>Pokemon={case p:Pokemon=>p.copy(energiaOriginal=p.energiaMaxima,estado=new Dormido())}
  val reposar=new Ataque(TipoNormal,30,Some(efectoReposar))
  val efectoEnforcarse:Pokemon=>Pokemon={case p:Pokemon=>p.copy(velocidadOriginal=p.velocidadOriginal+1)}
  val enfocarse=new Ataque(TipoNormal,30,Some(efectoEnforcarse))
  val efectoEndurecerse:Pokemon=>Pokemon={case p:Pokemon=>p.copy(energiaOriginal=p.energiaOriginal+5,estado=Paralizado)}
  val endurecerse= new Ataque(TipoNormal,30,Some(efectoEndurecerse))
  val ataqueElectrico= new Ataque(Electrico,35)
  val ataqueVolador= new Ataque(Volador,5)
  
}