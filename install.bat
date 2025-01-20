@echo off
setlocal

set INSTALL_DIR=%USERPROFILE%\ktimer

if not exist "%INSTALL_DIR%" (
    mkdir "%INSTALL_DIR%"
)

move /Y "ktimer.jar" "%INSTALL_DIR%\ktimer.jar"

echo @echo off > "%INSTALL_DIR%\ktimer.bat"
echo java -jar "%INSTALL_DIR%\ktimer.jar" %%* >> "%INSTALL_DIR%\ktimer.bat"

setx PATH "%INSTALL_DIR%;%PATH%"

echo Installation complete! You can now run 'ktimer' from any command prompt.
pause
