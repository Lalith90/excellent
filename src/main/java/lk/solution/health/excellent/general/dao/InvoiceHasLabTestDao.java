package lk.solution.health.excellent.general.dao;import lk.solution.health.excellent.general.entity.InvoiceHasLabTest;import lk.solution.health.excellent.lab.entity.Enum.LabTestStatus;import lk.solution.health.excellent.lab.entity.LabTest;import lk.solution.health.excellent.resource.entity.User;import lk.solution.health.excellent.transaction.entity.Invoice;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import java.time.LocalDate;import java.util.List;@Repositorypublic interface InvoiceHasLabTestDao extends JpaRepository<InvoiceHasLabTest, Integer> {    InvoiceHasLabTest findFirstByOrderByIdDesc();    List<InvoiceHasLabTest> findByLabTestStatus(LabTestStatus labTestStatus);    List<InvoiceHasLabTest> findByInvoiceAndLabTestStatus(Invoice invoice, LabTestStatus labTestStatus);    InvoiceHasLabTest findByInvoiceAndLabTest(Invoice invoice, LabTest labTest);    List<InvoiceHasLabTest> findByInvoice(Invoice invoice);    List<InvoiceHasLabTest> findByCreatedAt(LocalDate createdAt);    List<InvoiceHasLabTest> findByCreatedAtAndUser(LocalDate date, User user);    List<InvoiceHasLabTest> findByCreatedAtIsBetween(LocalDate from, LocalDate to);    InvoiceHasLabTest findByNumber(int number);    List<InvoiceHasLabTest> findByCreatedAtIsBetweenAndInvoice(LocalDate from, LocalDate to, Invoice invoice);/*method is ok    @Query(value = "select labtest_id from invoice_has_labtest where invoice_id=?1" , nativeQuery = true)    List<LabTest> findLabTestByInvoice(Invoice invoice);*/    //}