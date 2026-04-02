package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Usuario.
 * Essencial para os processos de autenticação e autorização do sistema.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Localiza um usuário no banco de dados através do seu nome de acesso (username).
     * O retorno em Optional é fundamental para o fluxo de segurança, permitindo
     * validar a existência do perfil antes de prosseguir com a verificação de senha.
     * * @param username O identificador de acesso do usuário.
     * @return Um Optional contendo o usuário, caso encontrado.
     */
    Optional<Usuario> findByUsername(String username);
}