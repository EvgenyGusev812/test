package spbpu.trkpo.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spbpu.trkpo.library.entity.Book;
import spbpu.trkpo.library.entity.BookType;
import spbpu.trkpo.library.entity.Client;
import spbpu.trkpo.library.entity.JournalRecord;
import spbpu.trkpo.library.repository.BookRepository;
import spbpu.trkpo.library.repository.BookTypeRepository;
import spbpu.trkpo.library.repository.ClientRepository;
import spbpu.trkpo.library.repository.JournalRecordRepository;
import spbpu.trkpo.library.service.BookService;
import spbpu.trkpo.library.service.BookTypeService;
import spbpu.trkpo.library.service.ClientService;
import spbpu.trkpo.library.service.JournalRecordService;
import spbpu.trkpo.library.utils.TestUtils;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JournalRecordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    JournalRecordService journalRecordService;

    @Autowired
    JournalRecordRepository journalRecordRepository;

    @Autowired
    BookTypeService bookTypeService;

    @Autowired
    BookTypeRepository bookTypeRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;


    @BeforeEach
    void setUp() {
        Client client = TestUtils.createClientEntity();
        client.setJournalRecords(null);
        Long clientId = clientService.saveClient(client);
        client = clientService.getById(clientId);

        BookType bookType = TestUtils.createBookTypeEntity();
        bookType.setBooks(null);
        Long bookTypeId = bookTypeService.saveBookType(bookType);
        bookType = bookTypeService.getById(bookTypeId);

        Book book = TestUtils.createBookEntity();
        book.setTypeId(null);
        book.setJournalRecords(null);
        Long bookId = bookService.saveBook(book);
        book = bookService.getById(bookId);
        book.setTypeId(bookTypeId);

        Set<Book> set = new HashSet<>();
        set.add(book);
        bookType.setBooks(set);
        bookTypeService.saveBookType(bookType);

        JournalRecord journalRecord = TestUtils.createJournalRecordEntity();
        journalRecord.setClientId(clientId);
        journalRecord.setBookId(bookId);
        Long journalRecordId = journalRecordService.saveJournalRecord(journalRecord);
        journalRecord = journalRecordService.getById(journalRecordId);

        Set<JournalRecord> set2 = new HashSet<>();
        set2.add(journalRecord);
        client.setJournalRecords(set2);
        clientService.saveClient(client);

        Set<JournalRecord> set3 = new HashSet<>();
        set3.add(journalRecord);
        book.setJournalRecords(set3);
        bookService.saveBook(book);
    }


    @Test
    void getJournalRecordByClientAndBookType() throws Exception {
        JournalRecord journalRecord = journalRecordService.getAll().get(0);
        Book book = bookService.getById(journalRecord.getBookId());
        BookType bookType = bookTypeService.getById(book.getTypeId());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/journal-records/by-client-and-type/" + journalRecord.getClientId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookType)))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getJournalRecordById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/journal-records/" + -1))
                .andExpect(status().is(404));
        JournalRecord journalRecord = journalRecordService.getAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/journal-records/" + journalRecord.getId()))
                .andExpect(jsonPath("$.id").value(journalRecord.getId()))
                .andExpect(status().isOk());
    }


    @Test
    void getAllJournalRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/journal-records/"))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    void saveJournalRecord() throws Exception {
        JournalRecord journalRecord = journalRecordService.getAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/journal-records/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(journalRecord)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteJournalRecord() throws Exception {
        JournalRecord journalRecord = journalRecordService.getAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/journal-records/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(journalRecord)))
                .andExpect(status().isOk());
    }

}
