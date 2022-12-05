package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        //WebDriver driver = new ChromeDriver();
        ChromeOptions cap = new ChromeOptions();
        cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.6:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.1.6:8001/tasks");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add toto
            driver.findElement(By.id("addTodo")).click();

            //ESCREVER DESCRIÇÃO
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", mensagem);
        } finally {
            //fechar o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add toto
            driver.findElement(By.id("addTodo")).click();

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", mensagem);
        } finally {
            //fechar o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add toto
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", mensagem);
        } finally {
            //fechar o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            //clicar em add toto
            driver.findElement(By.id("addTodo")).click();

            //ESCREVER DESCRIÇÃO
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar mensagem de sucesso
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("due date must not be in past", mensagem);
        } finally {
            //fechar o browser
            driver.quit();
        }
    }
}
