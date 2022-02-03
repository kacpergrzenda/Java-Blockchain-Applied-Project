package ie.gmit.blockchainmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.gmit.blockchainmanager.model.Blockchain;

public interface BlockchainRepo extends JpaRepository<Blockchain, Long>{

}
