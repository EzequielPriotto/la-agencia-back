package com.starfy.laAgencia.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parametro {

    @EmbeddedId
    Id id;

    @Getter
    @Setter
    @Embeddable
    public static class Id implements Serializable {

        private String clave;
        private String valor;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id1 = (Id) o;
            if (!clave.equals(id1.clave)) return false;
            return valor.equals(id1.valor);
        }

        @Override
        public int hashCode() {
            int result = clave.hashCode();
            result = 31 * result + valor.hashCode();
            return result;
        }
    }

}
