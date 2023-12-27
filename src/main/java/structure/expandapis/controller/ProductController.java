package structure.expandapis.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import structure.expandapis.dto.Payload;
import structure.expandapis.service.DynamicService;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final DynamicService dynamicService;

    @PostMapping("/add")
    @ApiOperation(value = "add new product")
    public ResponseEntity<Object> add(@RequestBody Payload payload) {
        dynamicService.saveDynamicRecords(payload);
        return new ResponseEntity<>(Map.of("table", payload.table(),
                "records-size", payload.records().size()), HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "get list of all products")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Map<String, Object>> products = dynamicService.findAll();
        return ResponseEntity.ok(products);
    }
}
