package ie.gmit.blockchainmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.gmit.blockchainmanager.model.BlockManager;
import ie.gmit.blockchainmanager.model.Blockchain;
import ie.gmit.sw.Block;

public interface BlockchainRepo extends JpaRepository<BlockManager, Long>{

}
