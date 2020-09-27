package njust.se2.librarymanagementsystemweb.service;

import njust.se2.librarymanagementsystemweb.dao.BookDao;
import njust.se2.librarymanagementsystemweb.dao.WantedListDao;
import njust.se2.librarymanagementsystemweb.pojo.Book;
import njust.se2.librarymanagementsystemweb.pojo.Category;
import njust.se2.librarymanagementsystemweb.pojo.WantedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDao bookDAO;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WantedListDao wantedListDao;

    /**
     * 返回所有书籍的列表
     * @return 书籍列表
     */
    public List<Book> list() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        return bookDAO.findAll(sort);
    }

    /**
     * 无主键则增添，有主键则更新。
     * @param book 书籍对象
     */
    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    /**
     * 根据id删除书籍
     * @param id 书的id
     */
    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    /**
     * 根据cid列出所有书籍信息
     * @param cid 类别id
     * @return 所有符合条件的书籍列表信息
     */
    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }

    /**
     * 根据关键字查询书籍信息
     * @param keywords  关键字
     * @return 符合关键字条件的书籍列表
     */
    public List<Book> Search(String keywords) {
        return bookDAO.findAllByBooknameLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }

    public List<Book> SearchById(int id) {
        return bookDAO.findAllById(id);
    }

    public List<WantedList> ListbyUsername(String username) {
        return wantedListDao.findAllByUsername(username);
    }

    public void addWantedList(WantedList wantedList) {
        System.out.println("addWantedList success");
        wantedListDao.save(wantedList);
    }

    /**
     * 查询是否存在
     *
     * @param bid 书id
     * @return 是否存在 boolean
     */
    public boolean isExist(int bid) {
        WantedList wantedList = getByBid(bid);
        return wantedList != null;
    }

    public WantedList getByBid(int bid) {
        return wantedListDao.findByBid(bid);
    }

    public void deletebyId(int id) {
        wantedListDao.deleteById(id);
    }
}
