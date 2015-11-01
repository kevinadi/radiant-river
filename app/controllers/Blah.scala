package controllers

import play.api.db._
import play.api.Play.current

import play.api.libs.json._
import play.api.libs.functional.syntax._

import anorm._



case class Blah(key:String, value:String, desc:String)

object Blah {

  /*
  Parse SQL query output into structure Blah
  */
  private val simple: RowParser[Blah] = {
    SqlParser.get[String]("KEY") ~
    SqlParser.get[String]("VALUE") ~
    SqlParser.get[String]("DESCRIPTION") map {
      case key ~ value ~ desc => Blah(key,value,desc)
    }
  }


  /*
  Reset database
  */
  def reset = {
    DB.withConnection { implicit conn =>
      val dropTableTest = SQL(
        """
        DROP TABLE IF EXISTS MASTER;
        CREATE TABLE MASTER (
          KEY TEXT,
          VALUE TEXT,
          INPUTDATE TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
        );

        DROP TABLE IF EXISTS DETAIL;
        CREATE TABLE DETAIL (
          KEY TEXT,
          DESCRIPTION TEXT,
          INPUTDATE TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
        );

        CREATE OR REPLACE FUNCTION update_modified_column() 
        RETURNS TRIGGER AS $$
        BEGIN
            NEW.INPUTDATE = now();
            RETURN NEW; 
        END;
        $$ language 'plpgsql';

        CREATE TRIGGER update_master_timestamp
        BEFORE UPDATE ON master
        FOR EACH ROW EXECUTE PROCEDURE update_modified_column();

        CREATE TRIGGER update_detail_timestamp
        BEFORE UPDATE ON detail
        FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
        """
      ).execute()
    }
    Blah.create(Blah("key1","val1","desc1"))
    Blah.create(Blah("key2","val2","desc2"))
    Blah.create(Blah("key3","val3","desc3"))
    Blah.create(Blah("key4","val4","desc4"))
    Blah.create(Blah("key5","val5","desc5"))
    Blah.create(Blah("key6","val6","desc6"))
    Blah.create(Blah("key7","val7","desc7"))
    Blah.findAll.length
  }


  /*
  Get all Blah from the database, returns Blah array
  */
  def findAll: List[Blah] = {
    DB.withConnection { implicit conn =>
      SQL(
        """
        SELECT MASTER.KEY,MASTER.VALUE,DETAIL.DESCRIPTION
        FROM MASTER
        JOIN DETAIL ON DETAIL.KEY = MASTER.KEY
        ORDER BY MASTER.KEY
        """
      ).as(Blah.simple *)
    }
  }


  /*
  Get some Blah with a specific key
  */
  def find(key:String): List[Blah] = {
    DB.withConnection { implicit conn =>
      SQL(
        """
        SELECT MASTER.KEY,MASTER.VALUE,DETAIL.DESCRIPTION
        FROM MASTER
        JOIN DETAIL ON DETAIL.KEY = MASTER.KEY
        WHERE MASTER.KEY = {key}
        """
      ).on('key -> key).as(Blah.simple *)
    }
  }


  /*
  Insert a new Blah into the database
  */
  def create(blah: Blah): Unit = {
    DB.withTransaction { implicit conn =>

      SQL(
        """
        INSERT INTO MASTER (KEY,VALUE) VALUES ({key},{value})
        """
      ).on('key -> blah.key, 'value -> blah.value)
       .executeUpdate()

      SQL(
        """
        INSERT INTO DETAIL (KEY,DESCRIPTION) VALUES ({key},{desc})
        """
      ).on('key -> blah.key, 'desc -> blah.desc)
       .executeUpdate()
    }
  }


  /*
  Update existing Blah
  */
  def update(blah: Blah): Unit = {
    DB.withTransaction { implicit conn =>

      SQL(
        """
        UPDATE MASTER SET VALUE = {value} WHERE KEY = {key}
        """
      ).on('key -> blah.key, 'value -> blah.value)
       .executeUpdate()

      SQL(
        """
        UPDATE DETAIL SET DESCRIPTION = {desc} WHERE KEY = {key}
        """
      ).on('key -> blah.key, 'desc -> blah.desc)
       .executeUpdate()
    }
  }




  /*
  Implicit to change Blah into Json
  Usage: Json.toJson(blah) -> returns JsValue
         Json.toJson(blah).toString -> returns the Json string
  */
  implicit val blahWrites = new Writes[Blah] {
    def writes(blah: Blah) = Json.obj(
      "key" -> blah.key,
      "value" -> blah.value,
      "desc" -> blah.desc
    )
  }


  /*
  Implicit to change Json to Blah
  Usage:  blahjson.as[Blah] -> uses the implicit reader without validation
          blahjson.asOpt[Blah] -> cast to option Blah without validation
          blahjson.validate[Blah].get -> cast to Blah with validation
          (blahjson \ "key").as[String] -> cast into string without validation
          (blahjson \ "key").asOpt[String] -> cast into option string without validation
          (blahjson \ "key").validate[String] -> validates the key is a string
          (blahjson \ "key").validate[String].getOrElse("default_key") -> get the validated key
  */
  implicit val blahReads: Reads[Blah] = (
    (JsPath \ "key").read[String] and
    (JsPath \ "value").read[String] and
    (JsPath \ "desc").read[String]
  )(Blah.apply _)

}
