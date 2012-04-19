// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package uk.co.vurt.taskhelper.server.domain.definition;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import uk.co.vurt.taskhelper.server.domain.definition.AutoTaskDefinition;

privileged aspect AutoTaskDefinition_Roo_Finder {
    
    public static TypedQuery<AutoTaskDefinition> AutoTaskDefinition.findAutoTaskDefinitionsByNameLike(java.lang.String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        name = name.replace('*', '%');
        if (name.charAt(0) != '%') {
            name = "%" + name;
        }
        if (name.charAt(name.length() - 1) != '%') {
            name = name + "%";
        }
        EntityManager em = AutoTaskDefinition.entityManager();
        TypedQuery<AutoTaskDefinition> q = em.createQuery("SELECT o FROM AutoTaskDefinition AS o WHERE LOWER(o.name) LIKE LOWER(:name)", AutoTaskDefinition.class);
        q.setParameter("name", name);
        return q;
    }
    
}