package com.Luciano.agendador_horarios.infrastructure.repository;

import com.Luciano.agendador_horarios.infrastructure.entity.Barbearia;
import com.Luciano.agendador_horarios.infrastructure.entity.Proprietario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Interface de acesso a dados para a entidade Barbearia.
 * Define as operações de busca e persistência personalizadas para o estabelecimento.
 */
@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, UUID> {

    /**
     * Localiza uma barbearia específica através do nome e do seu proprietário vinculado.
     */
    Barbearia findByNomeBarbeariaAndProprietario(String nomeBarbearia, Proprietario proprietario);

    /**
     * Remove uma barbearia do sistema baseando-se no nome.
     * A anotação @Transactional garante a integridade da operação de exclusão.
     */
    @Transactional
    void deleteByNomeBarbearia(String nomeBarbearia);

    /**
     * Realiza uma busca refinada utilizando ID, nome e rua para garantir a precisão do registro.
     */
    List<Barbearia> findByIdBarbeariaAndNomeBarbeariaAndRua(UUID idBarbearia, String nomeBarbearia, String rua);

    /**
     * Busca simples de barbearia pelo nome comercial.
     */
    Barbearia findByNomeBarbearia(String nomeBarbearia);

    /**
     * Filtra estabelecimentos que possuem o mesmo intervalo de funcionamento (abertura e fechamento).
     */
    Barbearia findByHorarioAberturaAndHorarioFechamento(LocalTime horarioAbertura, LocalTime horarioFechamento);

    /**
     * Localiza o estabelecimento através do número de telefone registrado.
     */
    Barbearia findByTelefoneBarbearia(String telefoneBarbearia);

    /**
     * Busca uma barbearia pelo endereço exato (Rua e Número).
     */
    Barbearia findByRuaAndNumeroRua(String rua, int numeroRua);

    /**
     * Retorna a barbearia vinculada a um perfil de proprietário específico.
     */
    Barbearia findByProprietario(Proprietario proprietario);
}