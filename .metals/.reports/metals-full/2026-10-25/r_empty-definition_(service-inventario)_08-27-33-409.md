error id: file:///C:/Users/Danie/Documents/UTP2026/SIIP/SIIP-service/service-inventario/src/main/java/com/siip/service_inventario/jar/model/Medicamento.java:
file:///C:/Users/Danie/Documents/UTP2026/SIIP/SIIP-service/service-inventario/src/main/java/com/siip/service_inventario/jar/model/Medicamento.java
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 826
uri: file:///C:/Users/Danie/Documents/UTP2026/SIIP/SIIP-service/service-inventario/src/main/java/com/siip/service_inventario/jar/model/Medicamento.java
text:
```scala
package com.siip.service_inventario.jar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicamento;

    @Column(unique = true, length = 30)
    private String codigoCan;

    private String nombreComercial;
    private String principioActivo;
    private Double precioUnitario;
    private String registroDigemid;

    public Long getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(Long idMedicamento) { this.idMedicamento = idMedicamento; }
    public String getCodigoCan() { return codigoCan; }
    public void setCodigoCan(String codigoCan) { this.codigoCan = codigoCan; }
    public String getNombreComercial() { return nombreComercial; }
    public void@@ setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getPrincipioActivo() { return principioActivo; }
    public void setPrincipioActivo(String principioActivo) { this.principioActivo = principioActivo; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getRegistroDigemid() { return registroDigemid; }
    public void setRegistroDigemid(String registroDigemid) { this.registroDigemid = registroDigemid; }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 