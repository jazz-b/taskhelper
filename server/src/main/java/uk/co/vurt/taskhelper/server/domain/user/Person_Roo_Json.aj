// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package uk.co.vurt.taskhelper.server.domain.user;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import uk.co.vurt.taskhelper.server.domain.user.Person;

privileged aspect Person_Roo_Json {
    
    public java.lang.String Person.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Person Person.fromJsonToPerson(java.lang.String json) {
        return new JSONDeserializer<Person>().use(null, Person.class).deserialize(json);
    }
    
    public static java.lang.String Person.toJsonArray(Collection<Person> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Person> Person.fromJsonArrayToPeople(java.lang.String json) {
        return new JSONDeserializer<List<Person>>().use(null, ArrayList.class).use("values", Person.class).deserialize(json);
    }
    
}