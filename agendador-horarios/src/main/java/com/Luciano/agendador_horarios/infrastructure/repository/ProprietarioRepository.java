package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Proprietario.
 * Responsável por gerenciar as informações dos donos de estabelecimentos no sistema.
 */
@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, UUID> {

    /**
     * Localiza um proprietário através da combinação de nome e telefone.
     * Útil para validação cruzada de dados cadastrais.
     */
    Proprietario findByNomeAndTelefone(String nome, String telefone);

    /**
     * Remove um proprietário do sistema pelo nome.
     * A anotação @Transactional assegura que a operação de exclusão seja executada em um contexto seguro.
     */
    @Transactional
    void deleteByNome(String nome);

    /**
     * Realiza uma consulta de alta precisão combinando ID, Nome e Email.
     * Geralmente utilizada em processos internos de segurança ou auditoria de conta.
     */
    List<Proprietario> findByIdProprietarioAndNomeAndEmail( UUID idProprietario, String nome, String email);

    /**
     * Busca um proprietário pelo nome completo.
     */
    Proprietario findByNome(String nome);

    /**
     * Localiza o proprietário através do número de telefone.
     * Frequentemente utilizado como chave de busca rápida.
     */
    Proprietario findByTelefone(String telefone);

    /**
     * Recupera o proprietário pelo endereço de e-mail.
     * Essencial para fluxos de autenticação e comunicação oficial.
     */
    Proprietario findByEmail(String email);
}