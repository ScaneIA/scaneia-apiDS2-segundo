package org.example.scaneia_dsii.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuario_acesso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioAcessoLogDau {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(name = "id_usuario", nullable = false)
     private Long idUsuario;

     @Column(name = "id_estrutura", nullable = false)
     private Long idEstrutura;

    @Column(name = "data_acesso", columnDefinition = "TIMESTAMPTZ", nullable = false)
    private OffsetDateTime dataAcesso;

    @Column(name = "data_logout", columnDefinition = "TIMESTAMPTZ", nullable = true)
    private OffsetDateTime dataLogout;

    @PrePersist
    public void prePersist() {
        if (this.dataAcesso == null) {
            this.dataAcesso = OffsetDateTime.now();
        }
    }
}

