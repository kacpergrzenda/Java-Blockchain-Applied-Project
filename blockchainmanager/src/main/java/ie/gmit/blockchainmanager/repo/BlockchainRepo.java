package ie.gmit.blockchainmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.gmit.blockchainmanager.model.BlockClone;


public interface BlockchainRepo extends JpaRepository<BlockClone, Long>{

}
