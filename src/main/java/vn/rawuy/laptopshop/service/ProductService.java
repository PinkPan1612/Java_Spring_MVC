package vn.rawuy.laptopshop.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.rawuy.laptopshop.domain.Cart;
import vn.rawuy.laptopshop.domain.CartDetail;
import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.OrderDetail;
import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.domain.User;
import vn.rawuy.laptopshop.repository.CartDetailRepository;
import vn.rawuy.laptopshop.repository.CartRepository;
import vn.rawuy.laptopshop.repository.OrderDetailRepository;
import vn.rawuy.laptopshop.repository.OrderRepository;
import vn.rawuy.laptopshop.repository.ProductRepository;
import vn.rawuy.laptopshop.repository.UserRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    private final OrderRepository orderRepsitory;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService, UserRepository userRepository,
            OrderRepository orderRepsitory, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepsitory = orderRepsitory;
        this.orderDetailRepository = orderDetailRepository;
    }

    // save product
    public Product handleCreateProduct(Product product) {
        System.out.println(product.toString());
        return this.productRepository.save(product);
    }

    public Product handleUpdateProduct(Product product) {
        System.out.println(product.toString());
        return this.productRepository.save(product);
    }

    // getAll product
    public List<Product> handleGetAllProduct() {
        return this.productRepository.findAll();
    }

    // get one product
    public Optional<Product> handleGetOneProductById(long id) {
        return this.productRepository.findById(id);
    }

    // delete one product
    public Product handleDeleteProductById(long id) {
        return this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpServletRequest request, int quantity) {
        HttpSession session = request.getSession(false);

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có cart chưa? nếu chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // create new cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                // save otherCart to db
                cart = this.cartRepository.save(otherCart);
            }

            // find product by id
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm xem đã từng được thêm vào giỏ hàng hay chưa
                boolean isExits = this.cartDetailRepository.existsByCartAndProduct(cart, realProduct);

                if (isExits) {
                    // nếu đã tồn tại thì tăng số lượng lên 1
                    CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart,
                            realProduct);
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldCartDetail);
                    return;
                }

                // if not exist, create new cart detail
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(realProduct);
                cartDetail.setPrice(realProduct.getPrice());
                cartDetail.setQuantity(quantity);

                // save cart_details
                this.cartDetailRepository.save(cartDetail);

                // update cart
                int s = cart.getSum() + 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
            }
        }

    }

    // tính tổng giá tiền của sản phẩm trong giỏ hàng
    public double totalPriceInOneCart(List<CartDetail> listCartDetail) {
        double totalCart = 0;
        for (CartDetail cd : listCartDetail) {
            totalCart += cd.getPrice() * cd.getQuantity();
        }
        return totalCart;
    }

    // fetchCartByUser
    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    // fetchCartDetailByID
    public CartDetail fetchCartDetailByID(long id) {
        return this.cartDetailRepository.findById(id).get();
    }

    // delete cart detail
    public void handleDeleteCartDetail(CartDetail cartDetail, Cart cart, HttpSession session) {
        int sum = 0;
        try {
            this.cartDetailRepository.delete(cartDetail);
            if (cart.getSum() > 1) {
                sum += cart.getSum() - 1;
                cart.setSum(sum);
                this.cartRepository.save(cart);
                session.setAttribute("sum", sum);
            } else if (cart.getSum() == 1) {
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // save cart
    public void hanldeSaveCart(Cart cart) {
        this.cartRepository.save(cart);
    }

    // delete cart
    public void hanldeDeleteCart(Cart cart) {
        this.cartRepository.delete(cart);
    }

    // handle Update before checkout (save new quantity in product detail)
    public void handleUpdateBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCD = cdOptional.get();
                currentCD.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCD);
            }
        }

    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                // create new order
                Order neworder = new Order();

                neworder.setUser(user);
                neworder.setReceiverName(receiverName);
                neworder.setReceiverAddress(receiverAddress);
                neworder.setReceiverPhone(receiverPhone);
                neworder.setStatus("PENDING");

                double totalPrice = 0;
                for (CartDetail cd : cartDetails) {
                    totalPrice += cd.getPrice() * cd.getQuantity();
                }
                neworder.setTotalPrice(totalPrice);
                // save order to db
                neworder = this.orderRepsitory.save(neworder);

                // CREATE ORDER DETAIL
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(neworder);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }
                // step 2: delete cart detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.delete(cd);
                }
                this.cartRepository.delete(cart);
                // step 3: update session
                session.setAttribute("sum", 0);
            }
        }

    }
}
