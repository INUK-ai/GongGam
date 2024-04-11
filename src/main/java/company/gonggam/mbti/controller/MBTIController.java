package company.gonggam.mbti.controller;

import company.gonggam.mbti.service.MBTIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mbti")
public class MBTIController {

    private final MBTIService mbtiService;
}
