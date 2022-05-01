package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyTest {
    private int amountValid = 500;
    private int amountInvalid = 100000;


    private DashboardPage shouldEnterDashboardPage() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldEnterValidLogin() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromCard2toCard1() {
        DashboardPage dashboardPage = shouldEnterDashboardPage();
        dashboardPage.dashboardPageVisible();
        int expected1 = dashboardPage.getBalanceCard1() + amountValid;
        int expected2 = dashboardPage.getBalanceCard2() - amountValid;
        val moneyTransfer = dashboardPage.topUpCard1();
        moneyTransfer.moneyTransferVisible();
        moneyTransfer.setTransferAmmount(amountValid);
        moneyTransfer.setNumberCardfrom(DataHelper.getCardNumber2());
        moneyTransfer.doTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard1());
        assertEquals(expected2, dashboardPage.getBalanceCard2());
    }

    @Test
    void shouldTransferMoneyFromCard1toCard2() {
        DashboardPage dashboardPage = shouldEnterDashboardPage();
        dashboardPage.dashboardPageVisible();
        int expected1 = dashboardPage.getBalanceCard2() + amountValid;
        int expected2 = dashboardPage.getBalanceCard1() - amountValid;
        val moneyTransfer = dashboardPage.topUpCard2();
        moneyTransfer.moneyTransferVisible();
        moneyTransfer.setTransferAmmount(amountValid);
        moneyTransfer.setNumberCardfrom(DataHelper.getCardNumber1());
        moneyTransfer.doTransfer();
        assertEquals(expected1, dashboardPage.getBalanceCard2());
        assertEquals(expected2, dashboardPage.getBalanceCard1());
    }
}