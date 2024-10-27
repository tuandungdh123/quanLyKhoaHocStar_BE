package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.PaymentDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "*")
public class PaymentApi {
    final PaymentService paymentService;

    @GetMapping("/getAllPayment")
    public ResponseObject<?> doGetAllPayment() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(paymentService.getAllPayment());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllPayment success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/payment/getAllPayment ", e);
        }
        return resultApi;
    }

    @PostMapping("/createPayment")
    public ResponseObject<?> doCreatePayment(@RequestBody PaymentDTO paymentDTO) {
        var resultApi = new ResponseObject<>();
        try {
            paymentService.createPayment(paymentDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Payment created successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/payment/createPayment ", e);
        }
        return resultApi;
    }

    @PutMapping("/updatePayment/{id}") // Specify the ID in the URL
    public ResponseObject<?> doUpdatePayment(@PathVariable Integer id, @RequestBody PaymentDTO paymentDTO) {
        var resultApi = new ResponseObject<>();
        try {
            // Call the service to update the payment status
            paymentService.updatePaymentStatus(id, paymentDTO.getStatus());
            resultApi.setSuccess(true);
            resultApi.setMessage("Payment status updated successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/payment/updatePayment/{} ", id, e);
        }
        return resultApi;
    }
}
