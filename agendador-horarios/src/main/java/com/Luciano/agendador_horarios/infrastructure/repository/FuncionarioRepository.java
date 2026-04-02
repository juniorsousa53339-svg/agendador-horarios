package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Funcionario.
 * Gerencia as operações de persistência dos profissionais e suas especialidades.
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    /**
     * Localiza um funcionário específico validando nome, telefone e especialidade.
     * Utilizado em fluxos de validação rigorosa ou auditoria de dados.
     */
    Funcionario findByNomeFuncionarioAndTelefoneFuncionarioAndEspecialidade(
            String nomeFuncionario, String telefoneFuncionario, String especialidade);

    /**
     * Busca um funcionário pelo seu identificador único (UUID).
     * O uso de Optional ajuda a evitar NullPointerException no serviço.
     */
    Optional<Funcionario> findById(UUID id);

    /**
     * Realiza uma busca textual por nomes que contenham o termo informado,
     * ignorando letras maiúsculas ou minúsculas. Ideal para campos de pesquisa (autocomplete).
     */
    List<Funcionario> findByNomeFuncionarioContainingIgnoreCase(String nome);
    List<Funcionario> findByIdFuncionarioAndNomeFuncionarioContainingIgnoreCase(Long idFuncionario, String nomeFuncionario);

    /**
     * Busca direta pelo nome completo do funcionário.
     */
    Funcionario findByNomeFuncionario(String nome);

    /**
     * Localiza o profissional através do seu número de contato.
     */
    Funcionario findByTelefoneFuncionario(String telefoneFuncionario);

    /**
     * Remove um funcionário do sistema baseando-se no nome.
     * Requer @Transactional por ser uma operação de modificação customizada.
     */
    @Transactional
    void deleteByNomeFuncionario(String nome);
}
