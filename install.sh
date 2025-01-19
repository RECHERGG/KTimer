#!/bin/bash

JAR_NAME="ktimer.jar"
INSTALL_DIR="/usr/local/bin"

if [ "$EUID" -ne 0 ]; then
  echo "Please run this script with sudo or as root."
  exit 1
fi

echo "Installing JAR file..."
cp "$JAR_NAME" "$INSTALL_DIR/ktimer.jar" || { echo "Failed to move JAR file."; exit 1; }

echo "#!/bin/bash
java -jar $INSTALL_DIR/ktimer.jar" > "$INSTALL_DIR/ktimer"

chmod +x "$INSTALL_DIR/ktimer"

echo "Installation complete! You can now run the program with 'ktimer'."
