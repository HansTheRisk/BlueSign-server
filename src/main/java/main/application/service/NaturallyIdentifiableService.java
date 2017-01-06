//package main.application.service;
//
//import main.application.domain.entity.naturallyIdentifiable.NaturallyIdentifiableEntity;
//import main.application.repository.NaturallyIdentifiableRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class NaturallyIdentifiableService<T extends NaturallyIdentifiableEntity> extends IdentifiableEntityService<T>{
//
//    @Autowired
//    public <S extends NaturallyIdentifiableRepository<T>> NaturallyIdentifiableService(S repository) {
//        super(repository);
//    }
//
//    public NaturallyIdentifiableEntity findByUUID(String uuid)
//    {
//        return ((NaturallyIdentifiableRepository)repository).findByUuid(uuid);
//    }
//
//    @Override
//    public T save(T object) {
//        object.setUuid(java.util.UUID.randomUUID().toString());
//        return super.save(object);
//    }
//
//}
