const { execSync } = require('child_process');

// 检查 Node.js 版本（要求 v14+）
function checkNodeVersion() {
  const requiredMajor = 14;
  const currentVersion = process.version;
  const majorVersion = parseInt(currentVersion.replace('v', '').split('.')[0], 10);

  if (majorVersion < requiredMajor) {
    console.error(`❌ Node.js 版本不符合要求`);
    console.error(`当前版本：${currentVersion}，所需最低版本：v${requiredMajor}.0.0`);
    console.error(`下载地址：https://nodejs.org/`);
    process.exit(1);
  } else {
    console.log(`✅ Node.js 版本检查通过：${currentVersion}`);
  }
}

// 检查 JDK 版本（要求 11+）
function checkJdkVersion() {
  try {
    // 执行 java -version 命令，捕获输出（错误流输出到标准输出）
    const output = execSync('java -version 2>&1', { encoding: 'utf-8' });
    // 从输出中提取版本号（兼容 JDK 11+ 格式，如 "11.0.18" 或 "17.0.6"）
    const versionMatch = output.match(/version "(\d+\.\d+\.\d+)"/);
    if (!versionMatch) {
      throw new Error("无法解析 JDK 版本");
    }

    const version = versionMatch[1];
    const majorVersion = parseInt(version.split('.')[0], 10);
    const requiredMajor = 11;

    if (majorVersion < requiredMajor) {
      console.error(`❌ JDK 版本不符合要求`);
      console.error(`当前版本：${version}，所需最低版本：${requiredMajor}.0.0`);
      console.error(`下载地址：https://www.oracle.com/java/technologies/downloads/`);
      process.exit(1);
    } else {
      console.log(`✅ JDK 版本检查通过：${version}`);
    }
  } catch (error) {
    console.error(`❌ 未检测到 JDK 环境`);
    console.error(`请安装 JDK ${requiredMajor} 及以上版本`);
    process.exit(1);
  }
}

// 执行检查
console.log("开始版本检查...");
checkNodeVersion();
checkJdkVersion();
console.log("所有版本检查通过！");