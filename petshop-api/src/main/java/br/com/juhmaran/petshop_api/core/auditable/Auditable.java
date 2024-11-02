package br.com.juhmaran.petshop_api.core.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

/**
 * Classe abstrata que adiciona informações de auditoria às entidades.
 * <p>
 * Esta classe armazena informações sobre o usuário que criou e atualizou a entidade,
 * bem como as datas de criação e atualização.
 * </p>
 *
 * <p><b>Exemplo de utilização:</b></p>
 * <pre>{@code
 * @Entity
 * public class Produto extends Auditable {
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private String nome;
 *
 *     // getters e setters
 * }
 * }</pre>
 *
 * <p>Esta classe utiliza o framework Spring Security para obter informações do usuário autenticado.</p>
 *
 * @author Juliane Maran
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    /**
     * O usuário que criou a entidade
     */
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    /**
     * A role do usuário que criou a entidade
     */
    @Column(name = "created_by_role", nullable = false, updatable = false)
    private String createdByRole;

    /**
     * A data de criação da entidade
     */
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    /**
     * O usuário que atualizou a entidade
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * A role do usuário que atualizou a entidade
     */
    @Column(name = "updated_by_role")
    private String updatedByRole;

    /**
     * A data da última atualização da entidade
     */
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * A data do último acesso à entidade
     */
    @Column(name = "last_accessed_date")
    private LocalDateTime lastAccessedDate;

    /**
     * Metodo executado antes de persistir a entidade
     */
    @PrePersist
    protected void onCreate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            this.createdBy = userDetails.getUsername();
            this.createdDate = LocalDateTime.now();
            this.createdByRole = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse("ROLE_USER");
        }
    }

    /**
     * Metodo executado antes de atualizar a entidade
     */
    @PreUpdate
    protected void onUpdate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            this.updatedBy = userDetails.getUsername();
            this.updatedDate = LocalDateTime.now();
            this.updatedByRole = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse("ROLE_USER");
        }
    }

    /**
     * Atualiza a data do último acesso à entidade
     */
    public void updateLastAccessedDate() {
        this.lastAccessedDate = LocalDateTime.now();
    }

}
