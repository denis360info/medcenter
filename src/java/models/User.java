/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ника
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByIdUser", query = "SELECT u FROM User u WHERE u.idUser = :idUser")
    , @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    , @NamedQuery(name = "User.findByPatronymic", query = "SELECT u FROM User u WHERE u.patronymic = :patronymic")
    , @NamedQuery(name = "User.findByTelephone", query = "SELECT u FROM User u WHERE u.telephone = :telephone")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private String status;
    @Column(name = "email")
    private String email;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "telephone")
    private Integer telephone;
    @OneToMany(mappedBy = "idUser")
    private List<MedicalRecord> medicalRecordList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Personal personal;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Doctor doctor;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Patient patient;

    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User(Integer idUser, String login, String password, String status, String email, String surname, String name, String patronymic, Integer telephone, List<MedicalRecord> medicalRecordList, Personal personal, Doctor doctor, Patient patient) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.status = status;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.telephone = telephone;
        this.medicalRecordList = medicalRecordList;
        this.personal = personal;
        this.doctor = doctor;
        this.patient = patient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.User[ idUser=" + idUser + " ]";
    }
    
}
