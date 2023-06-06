package ium.pethub.service;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.entity.Vet;
import ium.pethub.domain.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VetService {
    private final VetRepository vetRepository;


    @Transactional
    public void vetJoin(User user) throws Exception {
        Vet vet = new Vet(user);
        vetRepository.save(vet);
    }

//    public getByVetId(){
//
//    }
//
//    public getAll(){
//
//    }
//
//    public
}
