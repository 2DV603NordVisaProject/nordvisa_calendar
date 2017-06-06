# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
    config.vm.box = "ubuntu/trusty64"

    config.vm.provider "virtualbox" do |vb|
        vb.memory = "2048"
    end

    config.vm.network "forwarded_port", guest: 8080, host: 8080
    config.vm.network "forwarded_port", guest: 27017, host: 27017

    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        apt-get update
    SHELL

    # Installing Java 8
    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        add-apt-repository ppa:webupd8team/java
        apt-get update
        echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
        apt-get -y install oracle-java8-set-default
        sudo ln -s /usr/lib/jvm/java-8-oracle /usr/lib/jvm/default-java
    SHELL

    # Installing Gradle
    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        add-apt-repository ppa:cwchien/gradle
        apt-get update
        apt-get -y install gradle
        echo cd /vagrant >> .bashrc
    SHELL

    # Installing MongoDB
    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
        echo "deb http://repo.mongodb.org/apt/ubuntu "$(lsb_release -sc)"/mongodb-org/3.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list
        apt-get update
        apt-get install -y mongodb-org
    SHELL

    # Installing NodeJS
    config.vm.provision "shell", privileged: true, inline: <<-SHELL
        echo "export NODE_ENV=development" >> /home/vagrant/.bashrc
        curl -sL https://deb.nodesource.com/setup_7.x | sudo -E bash -
        apt-get install -y nodejs
    SHELL
end
