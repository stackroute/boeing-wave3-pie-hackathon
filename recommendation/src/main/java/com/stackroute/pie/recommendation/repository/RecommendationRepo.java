package com.stackroute.pie.recommendation.repository;


import com.stackroute.pie.recommendation.domain.disease;
import com.stackroute.pie.recommendation.domain.insured;
import com.stackroute.pie.recommendation.domain.insurer;
import com.stackroute.pie.recommendation.domain.policy;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RecommendationRepo extends Neo4jRepository <policy,Integer>{

    //@Query("match  p = (insured)-[:HAS_VIEWED]->(policy) return policy")
    //@Query("match  p = (user)-[:SUFFERING_FROM]->(disease)-[:HAS_A_POLICY]-(policy) return policy")
    //@Query("match  p = (user)-[:SUFFERING_FROM]->(d   isease)-[:HAS_A_POLICY]-(policy) where policy.minAge=35  return policy")
    @Query("MATCH p= (policy)-[r: POLICY_OF]->(insurer) RETURN policy")
    List<policy> findAllPolicy();


    @Query("match  p = (insured)-[:VIEWED_POLICY]->(policy) where insured.userName starts with $userName return policy")
    List<policy> findByuserName(String userName);

    @Query("match  p = (insured)-[:HAS_A_POLICY]->(policy) where policy.maxAge>$userAge AND policy.minAge<$userAge  return policy")
    List<policy> findByAge(Integer userAge);

    @Query("MATCH p= (policy)-[r: POLICY_OF]->(insurer)  where policy.gender=$userGender RETURN policy")
    List<policy> findByGender(List<String> userGender);

    @Query("MATCH p= (policy)-[r: POLICY_OF]->(insurer)  where policy.diseasesList=$policyDisease RETURN policy")
    List<policy> findByDisease(List<String> policyDisease);

   @Query("CREATE (Insurer:insurer{insurerLicense:{insurerLicense},insurerName:{insurerName}})")
    insurer saveInsurer(@Param("insurerId")int insurerId,@Param("insurerName")String insurerName,@Param("insurerLicense")String insurerLicense,@Param("insurerEmail")String insurerEmail,@Param("password")String password,@Param("insurerAddress")String insurerAddress,@Param("securityQuestion")String securityQuestion,@Param("securityAnswer")String securityAnswer);

   @Query("CREATE (Policy:policy{diseasesList:[value in {diseasesList} | toString(value)],gender:[value in {gender} | toString(value)],maxAge:{maxAge},minAge:{minAge},policyId:{policyId},policyName:{policyName},policyType:{policyType}})")
   policy savePolicy(@Param("policyId")int policyId, @Param("policyName")String policyName, @Param("policyType")String policyType, @Param("diseasesList")List<String> diseasesList, @Param("cashlessHospitals")int cashlessHospitals, @Param("waitingPeriod")int waitingPeriod, @Param("monthlyPremium")int monthlyPremium, @Param("yearlyPremium")int yearlyPremium, @Param("sumInsured")int sumInsured, @Param("minAge")int minAge, @Param("maxAge")int maxAge, @Param("location")String location,@Param("createdBy")String createdBy,@Param("updatedBy")String updatedBy,@Param("gender")List<String> gender);

   @Query("CREATE (Insured:insured{age:{age},familyMembers:{familyMembers},gender:{gender},userId:{userId},userName:{userName}})")
   insured saveInsured(@Param("userId")int userId,@Param("userName")String userName,@Param("gender")String gender,@Param("phoneNumber")String phoneNumber,@Param("emailId")String emailId,@Param("address")String address,@Param("name")String name,@Param("age")int age,@Param("remainingWaitingPeriod")int remainingWaitingPeriod,@Param("familyMembers")int familyMembers);


}
