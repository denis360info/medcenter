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
import models.Doctor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import models.Timetable;

/**
 *
 * @author Денис
 */
public class TimetableJpaController implements Serializable {

    public TimetableJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Timetable timetable) {
        if (timetable.getDoctorList() == null) {
            timetable.setDoctorList(new ArrayList<Doctor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Doctor> attachedDoctorList = new ArrayList<Doctor>();
            for (Doctor doctorListDoctorToAttach : timetable.getDoctorList()) {
                doctorListDoctorToAttach = em.getReference(doctorListDoctorToAttach.getClass(), doctorListDoctorToAttach.getIdUser());
                attachedDoctorList.add(doctorListDoctorToAttach);
            }
            timetable.setDoctorList(attachedDoctorList);
            em.persist(timetable);
            for (Doctor doctorListDoctor : timetable.getDoctorList()) {
                Timetable oldIdTimetableOfDoctorListDoctor = doctorListDoctor.getIdTimetable();
                doctorListDoctor.setIdTimetable(timetable);
                doctorListDoctor = em.merge(doctorListDoctor);
                if (oldIdTimetableOfDoctorListDoctor != null) {
                    oldIdTimetableOfDoctorListDoctor.getDoctorList().remove(doctorListDoctor);
                    oldIdTimetableOfDoctorListDoctor = em.merge(oldIdTimetableOfDoctorListDoctor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Timetable timetable) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Timetable persistentTimetable = em.find(Timetable.class, timetable.getIdTimetable());
            List<Doctor> doctorListOld = persistentTimetable.getDoctorList();
            List<Doctor> doctorListNew = timetable.getDoctorList();
            List<Doctor> attachedDoctorListNew = new ArrayList<Doctor>();
            for (Doctor doctorListNewDoctorToAttach : doctorListNew) {
                doctorListNewDoctorToAttach = em.getReference(doctorListNewDoctorToAttach.getClass(), doctorListNewDoctorToAttach.getIdUser());
                attachedDoctorListNew.add(doctorListNewDoctorToAttach);
            }
            doctorListNew = attachedDoctorListNew;
            timetable.setDoctorList(doctorListNew);
            timetable = em.merge(timetable);
            for (Doctor doctorListOldDoctor : doctorListOld) {
                if (!doctorListNew.contains(doctorListOldDoctor)) {
                    doctorListOldDoctor.setIdTimetable(null);
                    doctorListOldDoctor = em.merge(doctorListOldDoctor);
                }
            }
            for (Doctor doctorListNewDoctor : doctorListNew) {
                if (!doctorListOld.contains(doctorListNewDoctor)) {
                    Timetable oldIdTimetableOfDoctorListNewDoctor = doctorListNewDoctor.getIdTimetable();
                    doctorListNewDoctor.setIdTimetable(timetable);
                    doctorListNewDoctor = em.merge(doctorListNewDoctor);
                    if (oldIdTimetableOfDoctorListNewDoctor != null && !oldIdTimetableOfDoctorListNewDoctor.equals(timetable)) {
                        oldIdTimetableOfDoctorListNewDoctor.getDoctorList().remove(doctorListNewDoctor);
                        oldIdTimetableOfDoctorListNewDoctor = em.merge(oldIdTimetableOfDoctorListNewDoctor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = timetable.getIdTimetable();
                if (findTimetable(id) == null) {
                    throw new NonexistentEntityException("The timetable with id " + id + " no longer exists.");
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
            Timetable timetable;
            try {
                timetable = em.getReference(Timetable.class, id);
                timetable.getIdTimetable();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timetable with id " + id + " no longer exists.", enfe);
            }
            List<Doctor> doctorList = timetable.getDoctorList();
            for (Doctor doctorListDoctor : doctorList) {
                doctorListDoctor.setIdTimetable(null);
                doctorListDoctor = em.merge(doctorListDoctor);
            }
            em.remove(timetable);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Timetable> findTimetableEntities() {
        return findTimetableEntities(true, -1, -1);
    }

    public List<Timetable> findTimetableEntities(int maxResults, int firstResult) {
        return findTimetableEntities(false, maxResults, firstResult);
    }

    private List<Timetable> findTimetableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Timetable.class));
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

    public Timetable findTimetable(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Timetable.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimetableCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Timetable> rt = cq.from(Timetable.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
