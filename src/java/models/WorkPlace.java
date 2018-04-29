/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Денис
 */
@Entity
@Table(name = "work_place")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkPlace.findAll", query = "SELECT w FROM WorkPlace w")
    , @NamedQuery(name = "WorkPlace.findByIdWorkPlace", query = "SELECT w FROM WorkPlace w WHERE w.idWorkPlace = :idWorkPlace")
    , @NamedQuery(name = "WorkPlace.findByWorkPlace", query = "SELECT w FROM WorkPlace w WHERE w.workPlace = :workPlace")
    , @NamedQuery(name = "WorkPlace.findByPost", query = "SELECT w FROM WorkPlace w WHERE w.post = :post")
    , @NamedQuery(name = "WorkPlace.findByTelephone", query = "SELECT w FROM WorkPlace w WHERE w.telephone = :telephone")})
public class WorkPlace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_work_place")
    private Integer idWorkPlace;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "post")
    private String post;
    @Column(name = "telephone")
    private Integer telephone;
    @OneToMany(mappedBy = "idWorkPlace")
    private List<MedicalRecord> medicalRecordList;

    public WorkPlace() {
    }

    public WorkPlace(Integer idWorkPlace) {
        this.idWorkPlace = idWorkPlace;
    }

    public Integer getIdWorkPlace() {
        return idWorkPlace;
    }

    public void setIdWorkPlace(Integer idWorkPlace) {
        this.idWorkPlace = idWorkPlace;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    @XmlTransient
    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }

    public void setMedicalRecordList(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordList = medicalRecordList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWorkPlace != null ? idWorkPlace.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkPlace)) {
            return false;
        }
        WorkPlace other = (WorkPlace) object;
        if ((this.idWorkPlace == null && other.idWorkPlace != null) || (this.idWorkPlace != null && !this.idWorkPlace.equals(other.idWorkPlace))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.WorkPlace[ idWorkPlace=" + idWorkPlace + " ]";
    }
    
}
