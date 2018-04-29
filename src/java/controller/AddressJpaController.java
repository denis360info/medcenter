/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.MedicalRecord;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Address;

/**
 *
 * @author Денис
 */
public class AddressJpaController implements Serializable {

    public AddressJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) {
        if (address.getMedicalRecordList() == null) {
            address.setMedicalRecordList(new ArrayList<MedicalRecord>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MedicalRecord> attachedMedicalRecordList = new ArrayList<MedicalRecord>();
            for (MedicalRecord medicalRecordListMedicalRecordToAttach : address.getMedicalRecordList()) {
                medicalRecordListMedicalRecordToAttach = em.getReference(medicalRecordListMedicalRecordToAttach.getClass(), medicalRecordListMedicalRecordToAttach.getIdMedicalRecord());
                attachedMedicalRecordList.add(medicalRecordListMedicalRecordToAttach);
            }
            address.setMedicalRecordList(attachedMedicalRecordList);
            em.persist(address);
            for (MedicalRecord medicalRecordListMedicalRecord : address.getMedicalRecordList()) {
                Address oldIdAddressOfMedicalRecordListMedicalRecord = medicalRecordListMedicalRecord.getIdAddress();
                medicalRecordListMedicalRecord.setIdAddress(address);
                medicalRecordListMedicalRecord = em.merge(medicalRecordListMedicalRecord);
                if (oldIdAddressOfMedicalRecordListMedicalRecord != null) {
                    oldIdAddressOfMedicalRecordListMedicalRecord.getMedicalRecordList().remove(medicalRecordListMedicalRecord);
                    oldIdAddressOfMedicalRecordListMedicalRecord = em.merge(oldIdAddressOfMedicalRecordListMedicalRecord);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Address address) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address persistentAddress = em.find(Address.class, address.getIdAddress());
            List<MedicalRecord> medicalRecordListOld = persistentAddress.getMedicalRecordList();
            List<MedicalRecord> medicalRecordListNew = address.getMedicalRecordList();
            List<MedicalRecord> attachedMedicalRecordListNew = new ArrayList<MedicalRecord>();
            for (MedicalRecord medicalRecordListNewMedicalRecordToAttach : medicalRecordListNew) {
                medicalRecordListNewMedicalRecordToAttach = em.getReference(medicalRecordListNewMedicalRecordToAttach.getClass(), medicalRecordListNewMedicalRecordToAttach.getIdMedicalRecord());
                attachedMedicalRecordListNew.add(medicalRecordListNewMedicalRecordToAttach);
            }
            medicalRecordListNew = attachedMedicalRecordListNew;
            address.setMedicalRecordList(medicalRecordListNew);
            address = em.merge(address);
            for (MedicalRecord medicalRecordListOldMedicalRecord : medicalRecordListOld) {
                if (!medicalRecordListNew.contains(medicalRecordListOldMedicalRecord)) {
                    medicalRecordListOldMedicalRecord.setIdAddress(null);
                    medicalRecordListOldMedicalRecord = em.merge(medicalRecordListOldMedicalRecord);
                }
            }
            for (MedicalRecord medicalRecordListNewMedicalRecord : medicalRecordListNew) {
                if (!medicalRecordListOld.contains(medicalRecordListNewMedicalRecord)) {
                    Address oldIdAddressOfMedicalRecordListNewMedicalRecord = medicalRecordListNewMedicalRecord.getIdAddress();
                    medicalRecordListNewMedicalRecord.setIdAddress(address);
                    medicalRecordListNewMedicalRecord = em.merge(medicalRecordListNewMedicalRecord);
                    if (oldIdAddressOfMedicalRecordListNewMedicalRecord != null && !oldIdAddressOfMedicalRecordListNewMedicalRecord.equals(address)) {
                        oldIdAddressOfMedicalRecordListNewMedicalRecord.getMedicalRecordList().remove(medicalRecordListNewMedicalRecord);
                        oldIdAddressOfMedicalRecordListNewMedicalRecord = em.merge(oldIdAddressOfMedicalRecordListNewMedicalRecord);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = address.getIdAddress();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getIdAddress();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<MedicalRecord> medicalRecordList = address.getMedicalRecordList();
            for (MedicalRecord medicalRecordListMedicalRecord : medicalRecordList) {
                medicalRecordListMedicalRecord.setIdAddress(null);
                medicalRecordListMedicalRecord = em.merge(medicalRecordListMedicalRecord);
            }
            em.remove(address);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Address.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Address findAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Address> rt = cq.from(Address.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
