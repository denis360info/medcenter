/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Doctor;
import models.Patient;
import models.Service;
import models.Ticket;

/**
 *
 * @author Денис
 */
public class TicketJpaController implements Serializable {

    public TicketJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor idDoctor = ticket.getIdDoctor();
            if (idDoctor != null) {
                idDoctor = em.getReference(idDoctor.getClass(), idDoctor.getIdUser());
                ticket.setIdDoctor(idDoctor);
            }
            Patient idPatient = ticket.getIdPatient();
            if (idPatient != null) {
                idPatient = em.getReference(idPatient.getClass(), idPatient.getIdUser());
                ticket.setIdPatient(idPatient);
            }
            Service idService = ticket.getIdService();
            if (idService != null) {
                idService = em.getReference(idService.getClass(), idService.getIdService());
                ticket.setIdService(idService);
            }
            em.persist(ticket);
            if (idDoctor != null) {
                idDoctor.getTicketList().add(ticket);
                idDoctor = em.merge(idDoctor);
            }
            if (idPatient != null) {
                idPatient.getTicketList().add(ticket);
                idPatient = em.merge(idPatient);
            }
            if (idService != null) {
                idService.getTicketList().add(ticket);
                idService = em.merge(idService);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ticket ticket) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ticket persistentTicket = em.find(Ticket.class, ticket.getIdTicket());
            Doctor idDoctorOld = persistentTicket.getIdDoctor();
            Doctor idDoctorNew = ticket.getIdDoctor();
            Patient idPatientOld = persistentTicket.getIdPatient();
            Patient idPatientNew = ticket.getIdPatient();
            Service idServiceOld = persistentTicket.getIdService();
            Service idServiceNew = ticket.getIdService();
            if (idDoctorNew != null) {
                idDoctorNew = em.getReference(idDoctorNew.getClass(), idDoctorNew.getIdUser());
                ticket.setIdDoctor(idDoctorNew);
            }
            if (idPatientNew != null) {
                idPatientNew = em.getReference(idPatientNew.getClass(), idPatientNew.getIdUser());
                ticket.setIdPatient(idPatientNew);
            }
            if (idServiceNew != null) {
                idServiceNew = em.getReference(idServiceNew.getClass(), idServiceNew.getIdService());
                ticket.setIdService(idServiceNew);
            }
            ticket = em.merge(ticket);
            if (idDoctorOld != null && !idDoctorOld.equals(idDoctorNew)) {
                idDoctorOld.getTicketList().remove(ticket);
                idDoctorOld = em.merge(idDoctorOld);
            }
            if (idDoctorNew != null && !idDoctorNew.equals(idDoctorOld)) {
                idDoctorNew.getTicketList().add(ticket);
                idDoctorNew = em.merge(idDoctorNew);
            }
            if (idPatientOld != null && !idPatientOld.equals(idPatientNew)) {
                idPatientOld.getTicketList().remove(ticket);
                idPatientOld = em.merge(idPatientOld);
            }
            if (idPatientNew != null && !idPatientNew.equals(idPatientOld)) {
                idPatientNew.getTicketList().add(ticket);
                idPatientNew = em.merge(idPatientNew);
            }
            if (idServiceOld != null && !idServiceOld.equals(idServiceNew)) {
                idServiceOld.getTicketList().remove(ticket);
                idServiceOld = em.merge(idServiceOld);
            }
            if (idServiceNew != null && !idServiceNew.equals(idServiceOld)) {
                idServiceNew.getTicketList().add(ticket);
                idServiceNew = em.merge(idServiceNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ticket.getIdTicket();
                if (findTicket(id) == null) {
                    throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.");
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
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getIdTicket();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.", enfe);
            }
            Doctor idDoctor = ticket.getIdDoctor();
            if (idDoctor != null) {
                idDoctor.getTicketList().remove(ticket);
                idDoctor = em.merge(idDoctor);
            }
            Patient idPatient = ticket.getIdPatient();
            if (idPatient != null) {
                idPatient.getTicketList().remove(ticket);
                idPatient = em.merge(idPatient);
            }
            Service idService = ticket.getIdService();
            if (idService != null) {
                idService.getTicketList().remove(ticket);
                idService = em.merge(idService);
            }
            em.remove(ticket);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ticket> findTicketEntities() {
        return findTicketEntities(true, -1, -1);
    }

    public List<Ticket> findTicketEntities(int maxResults, int firstResult) {
        return findTicketEntities(false, maxResults, firstResult);
    }

    private List<Ticket> findTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ticket.class));
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

    public Ticket findTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    public int getTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ticket> rt = cq.from(Ticket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
